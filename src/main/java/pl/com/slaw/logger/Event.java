package pl.com.slaw.logger;

//----------------------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//	Copyright © 2007 - 2018 Tangible Software Solutions Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to convert C# events to Java.
//----------------------------------------------------------------------------------------
public final class Event<T>
{
	private final java.util.Map<String, T> namedListeners = new HashMap<>();
        private final java.util.List<T> anonymousListeners = new java.util.ArrayList<>();
        
	public void addListener(String methodName, T namedEventHandlerMethod)
	{
		if (!namedListeners.containsKey(methodName))
			namedListeners.put(methodName, namedEventHandlerMethod);
	}
	public void removeListener(String methodName)
	{
		if (namedListeners.containsKey(methodName))
			namedListeners.remove(methodName);
	}

	
	public void addListener(T unnamedEventHandlerMethod)
	{
		anonymousListeners.add(unnamedEventHandlerMethod);
	}

	public List<T> listeners()
	{
		List<T> allListeners = new ArrayList<>();
		allListeners.addAll(namedListeners.values());
		allListeners.addAll(anonymousListeners);
		return allListeners;
	}
}