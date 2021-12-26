package com.example.haffman_encode;

import java.io.Serializable;
import java.util.Map;

public class CodeMapper implements Serializable {
     public Map<String, String> encodeMapper;

    public CodeMapper(Map<String, String> encodeMapper, Map<String, String> decodeMapper) {
        this.encodeMapper = encodeMapper;
        this.decodeMapper = decodeMapper;
    }

    public Map<String, String> decodeMapper;
}
