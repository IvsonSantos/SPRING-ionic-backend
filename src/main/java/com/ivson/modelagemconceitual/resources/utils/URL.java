package com.ivson.modelagemconceitual.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Para converter o URL em uma lista de inteiros (1,3,4)
 * @author Santo
 *
 */
public class URL {

	/**
	 * Recebe um param em lista e retorna os elementos
	 * @param s
	 * @return
	 */
	public static List<Integer> decodeIntList(String s) {
//		String[] vet = s.split(",");
//		List<Integer> list = new ArrayList<>();
//		for (int i=0; i < vet.length; i++) {
//			list.add(Integer.parseInt(vet[i]));
//		}
//		return list;
		
		return Arrays.asList(s.split(","))
					 .stream()
					 		  .map(x -> Integer.parseInt(x))
					 		  .collect(Collectors.toList());
	}
	
	/** 
	 * Desencoda uma string
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String decodeParam(String s)  {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
