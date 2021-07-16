package com.example.countrylist.base.constants

class Utils {
    companion object {
        const val CODE = "code"
        const val ERROR_MESSAGE = "Something goes wrong :("
        const val COUNTRY_LIST_RESPONSE = "{\n" +
                "  \"data\": {\n" +
                "    \"countries\": [\n" +
                "      {\n" +
                "        \"__typename\": \"Country\",\n" +
                "        \"code\": \"AD\",\n" +
                "        \"name\": \"Andorra\",\n" +
                "        \"capital\": \"Andorra la Vella\",\n" +
                "        \"continent\": {\n" +
                "          \"__typename\": \"Continent\",\n" +
                "          \"name\": \"Europe\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"__typename\": \"Country\",\n" +
                "        \"code\": \"AE\",\n" +
                "        \"name\": \"United Arab Emirates\",\n" +
                "        \"capital\": \"Abu Dhabi\",\n" +
                "        \"continent\": {\n" +
                "          \"__typename\": \"Continent\",\n" +
                "          \"name\": \"Asia\"\n" +
                "        }\n" +
                "      }]" +
                "   }" +
                "}"
        const val DETAILS_RESPONSE = "{\n" +
                "  \"data\": {\n" +
                "    \"country\": {\n" +
                "      \"__typename\": \"Country\",\n" +
                "      \"name\": \"Ukraine\",\n" +
                "      \"native\": \"Україна\",\n" +
                "      \"phone\": \"380\",\n" +
                "      \"capital\": \"Kyiv\",\n" +
                "      \"continent\": {\n" +
                "        \"__typename\": \"Continent\",\n" +
                "        \"name\": \"Europe\"\n" +
                "      },\n" +
                "      \"emoji\": \"\uD83C\uDDFA\uD83C\uDDE6\",\n" +
                "      \"emojiU\": \"U+1F1FA U+1F1E6\",\n" +
                "      \"currency\": \"UAH\",\n" +
                "      \"languages\": [\n" +
                "        {\n" +
                "          \"__typename\": \"Language\",\n" +
                "          \"name\": \"Ukrainian\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}"
        const val TEST_COUNTRY_NAME = "Andorra"
        const val TEST_COUNTRY_CODE = "AD"
    }
}