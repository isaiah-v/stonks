/**
 * Parses the URL's query string.
 * 
 * Example: ?test=abc
 * Usage: qs['test'] = 'abc'
 */
const qs = (function(a) {
    if (a == "") return {};
    var b = {};
    for (var i = 0; i < a.length; ++i)
    {
        var p=a[i].split('=', 2);
        if (p.length == 1)
            b[p[0]] = "";
        else
            b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
    }
    return b;
})(window.location.search.substr(1).split('&'));

/**
 * Base64Url utility
 */
class Base64Url {
	static encode(str) {
		var base64 = btoa(str);
		return base64.replace(/\+/g, '-').replace(/\//g,'_').replace(/=/g, '');
	}
	
	static decode(str) {
		var base64 = str.replace(/-/g, '+').replace(/_/g,'/');
		return atob(base64); 
	}
}