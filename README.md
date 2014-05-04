<h1>reflective-resource-bundle</h1>
<hr/>
<p>Utility class that permits self-referencing ResourceBundles</p>
<hr/>
<p><b>Update</b>: Added code to get this working for the <a href="http://www.stripesframework.org">Stripes Framework</a></p>
<hr/>
<p>This was written to address a deficiency with Java ResourceBundles: messages cannot reference other messages. This directly, egregiously violates the DRY (don't repeat yourself) programming principle.</p>
<h2>Example</h2>
<pre>
company.service.phone=555-1212
company.service.email=help@company.com
my.error.message=Problem accessing account!\
  Please call customer service at 555-1212 \
  or email us at help@company.com
</pre>
<p>The problem is quickly evident: if you have several messages that all reference the customer service email or phone number, and that value changes at any point, you now have to update hundreds of resource messages.</p>
<h2>Solution</h2>
<pre>
company.service.phone=555-1212
company.service.email=help@company.com
my.error.message=Problem accessing account!\
  Please call customer service at <b>${company.service.phone}</b> \
  or email us at <b>${company.service.email}</b>
</pre>
<p>The advantages should be fairly obvious.</p>
