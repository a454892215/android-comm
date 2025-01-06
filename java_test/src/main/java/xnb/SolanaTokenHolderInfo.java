package xnb;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolanaTokenHolderInfo {
    private static final String SOLANA_RPC_URL = "https://api.mainnet-beta.solana.com";
    private static final String TOKEN_PROGRAM_ID = "TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA";

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        String pythMintAddress = "AuyuBikfmktzGA4m7GkrZ199m3T5D5UFY52P5B9C1Vri"; // 替换为 Pyth Token 的 Mint 地址
        List<String> tokenHolders = getTokenAccountsByMint(pythMintAddress);

        System.out.println("Token holders count: " + tokenHolders.size());
        for (String holderAddress : tokenHolders) {
            String creationTime = getAddressCreationTime(holderAddress);
            System.out.println("Address: " + holderAddress + ", Created at: " + creationTime);
        }
    }

    private static List<String> getTokenAccountsByMint(String mintAddress) throws IOException {
        List<String> tokenHolders = new ArrayList<>();
        String requestBody = "{"
                + "\"jsonrpc\":\"2.0\","
                + "\"id\":1,"
                + "\"method\":\"getProgramAccounts\","
                + "\"params\":["
                + "\"" + TOKEN_PROGRAM_ID + "\","
                + "{"
                + "\"filters\":["
                + "{\"dataSize\":165},"
                + "{\"memcmp\":{\"offset\":0,\"bytes\":\"" + mintAddress + "\"}}"
                + "]"
                + "}"
                + "]"
                + "}";

        Request request = new Request.Builder()
                .url(SOLANA_RPC_URL)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                JsonNode jsonResponse = objectMapper.readTree(response.body().string());
                JsonNode result = jsonResponse.get("result");
                if (result != null && result.isArray()) {
                    for (JsonNode account : result) {
                        tokenHolders.add(account.get("pubkey").asText());
                    }
                }
            }
        }

        return tokenHolders;
    }

    private static String getAddressCreationTime(String address) throws IOException {
        String requestBody = "{"
                + "\"jsonrpc\":\"2.0\","
                + "\"id\":1,"
                + "\"method\":\"getConfirmedSignaturesForAddress2\","
                + "\"params\":["
                + "\"" + address + "\","
                + "{\"limit\":1}"
                + "]"
                + "}";

        Request request = new Request.Builder()
                .url(SOLANA_RPC_URL)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                JsonNode jsonResponse = objectMapper.readTree(response.body().string());
                JsonNode result = jsonResponse.get("result");
                if (result != null && result.isArray() && result.size() > 0) {
                    JsonNode firstTransaction = result.get(0);
                    JsonNode blockTime = firstTransaction.get("blockTime");
                    if (blockTime != null) {
                        return blockTime.asText();
                    }
                }
            }
        }

        return "Unknown";
    }
}
