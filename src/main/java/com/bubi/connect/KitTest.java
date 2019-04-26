package com.bubi.connect;

import io.bumo.SDK;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.response.result.data.Signature;

public class KitTest {
    public static void main(String[] args) {
        String url = "http://140.143.38.123:36002";
        SDK sdk = SDK.getInstance(url);
        String payload = "\"use strict\";\n" +
                "function init(input)\n" +
                "{    /* init whatever you want */ return;}\n" +
                "function query(input)\n" +
                "{\n" +
                "    let keyValue = JSON.parse(input).key;\n" +
                "    let data = storageLoad(keyValue);\n" +
                "    if(data === false){return 'false';}\n" +
                "    return data;\n" +
                "}\n" +
                "\n" +
                "function main(input)\n" +
                "{\n" +
                "    let obj =JSON.parse(input);\n" +
                "    let key = obj.key;\n" +
                "    let value = obj.value;\n" +
                "    let value_store ;\n" +
                "    let query_result = storageLoad(key);\n" +
                "    if(query_result === false)\n" +
                "    {\n" +
                "        value_store = value;\n" +
                "    }else\n" +
                "    {\n" +
                "        let arr = query_result.split(\",\");\n" +
                "        let i=0;\n" +
                "        let flag =0;\n" +
                "        for(i =0;i<arr.length;i+=1)\n" +
                "        {\n" +
                "             if(arr[i] === value)\n" +
                "             {\n" +
                "                flag = 1;\n" +
                "             }\n" +
                "        }\n" +
                "        if(flag === 0)\n" +
                "        {\n" +
                "            arr.push(value);\n" +
                "        }\n" +
                "        value_store = arr.toString();\n" +
                "    }\n" +
                "    storageStore(key,value_store);\n" +
                "    return ;\n" +
                "}\n";
        String senderAddress = "buQd4TBqSbHw3EoLMnSmH4SJFMkHUtEQbUvz";

        BaseOperation operation = BumoKit.conctractCreateOperation(payload,senderAddress);
        String transactioBlob = BumoKit.seralizeTransaction(sdk,senderAddress,operation);
        String privateKey = "privbzYwbUSCwQZq7eXgu4C9cpqrQD4enXY49V7qUrifc6fCtiPmBhWA";
        Signature[] signatures = BumoKit.signTransaction(privateKey,sdk,transactioBlob);
        String hash = BumoKit.submitTransaction(sdk,transactioBlob,signatures);
        System.out.println(hash);
    }
}
