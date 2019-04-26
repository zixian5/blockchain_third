package com.bubi.connect;

import io.bumo.SDK;
import io.bumo.model.request.AccountGetNonceRequest;
import io.bumo.model.request.TransactionBuildBlobRequest;
import io.bumo.model.request.TransactionSignRequest;
import io.bumo.model.request.TransactionSubmitRequest;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.request.operation.ContractCreateOperation;
import io.bumo.model.request.operation.ContractInvokeByAssetOperation;
import io.bumo.model.response.AccountGetNonceResponse;
import io.bumo.model.response.TransactionBuildBlobResponse;
import io.bumo.model.response.TransactionSignResponse;
import io.bumo.model.response.TransactionSubmitResponse;
import io.bumo.model.response.result.AccountGetNonceResult;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import io.bumo.model.response.result.data.Signature;

public class BumoKit {
    public static Signature[] signTransaction(String privateKey,SDK sdk,String transactionBlob)
    {
        Signature[] signatures = null;
// Sign transaction BLob
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        transactionSignRequest.addPrivateKey(privateKey);
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);
        if (transactionSignResponse.getErrorCode() == 0) {
            signatures = transactionSignResponse.getResult().getSignatures();
        } else {
            System.out.println("SignError: " + transactionSignResponse.getErrorDesc());
        }
        return signatures;
    }

    public static ContractCreateOperation conctractCreateOperation(String payload,String sourceAddress)
    {
        ContractCreateOperation operation = new ContractCreateOperation();
        operation.setPayload(payload);
        operation.setInitBalance(1000000000000000L);
        return operation;
    }

    public static ContractInvokeByAssetOperation contractInvokeByAssetOperation(String input ,String contractAddress)
    {
        ContractInvokeByAssetOperation operation= new ContractInvokeByAssetOperation();
        operation.setContractAddress(contractAddress);
        operation.setInput(input);
        return operation;
    }

    public static String seralizeTransaction(SDK sdk,String senderAddress, BaseOperation operation)
    {
        AccountGetNonceRequest getNonceRequest = new AccountGetNonceRequest();
        getNonceRequest.setAddress(senderAddress);
        AccountGetNonceResponse getNonceResponse = sdk.getAccountService().getNonce(getNonceRequest);

// 赋值nonce
        long nonce = 0l;
        if (getNonceResponse.getErrorCode() == 0) {
            AccountGetNonceResult result = getNonceResponse.getResult();
            System.out.println("nonce: " + result.getNonce());
            nonce = result.getNonce();
        }
        else {
            System.out.println("error" + getNonceResponse.getErrorDesc());
        }

        TransactionBuildBlobRequest request = new TransactionBuildBlobRequest();
        request.addOperation(operation);
        request.setNonce(nonce+1);
        request.setSourceAddress(senderAddress);
        request.setGasPrice(1000L);
        request.setFeeLimit(10000000000l);
        TransactionBuildBlobResponse response = sdk.getTransactionService().buildBlob(request);
        String hash =null;
        if (response.getErrorCode() == 0) {
            TransactionBuildBlobResult result = response.getResult();
            // transactionBlob = result.getTransactionBlob();
          //  System.out.println(JSON.toJSONString(result, true));
            hash = response.getResult().getTransactionBlob();
        } else {
            System.out.println("BuildError: " + response.getErrorDesc());
        }

        return hash;
    }

    public static String submitTransaction(SDK sdk,String transactionBlob, Signature[] signatures) {
        String  hash = null;

// Submit transaction
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(signatures);
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) {
            hash = transactionSubmitResponse.getResult().getHash();
        } else {
            System.out.println("submitError: " + transactionSubmitResponse.getErrorDesc());
        }
        return  hash ;
    }
}

















