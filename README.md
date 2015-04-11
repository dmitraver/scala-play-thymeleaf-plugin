# Thymleaf module for Play Framework(Scala)
A module for Play Framework(Scala) to use [Thymeleaf] (http://thymeleaf.org) template engine as an alternative to [Twirl] (https://github.com/playframework/twirl), which is a standard Play Framework template engine.

Thymeleaf is an **XML / XHTML / HTML5** template engine that is suited for serving XHTML/HTML5 at the view layer of web applications. It is based on XML tags and attributes that define the execution of predefined logic on the DOM (Document Object Model), instead of explicitly writing that logic as code inside the template.

##Installation
To use this module add it as a dependency to your build file
```
"com.github.dmitraver" %% "play-thymeleaf-plugin" % "1.0"
```

## Sample application
To see this module in action check out the project and run sample with the following commands:
```
cd project_sources_root
sbt "project sample" run
```
Sample represents a simple web application which is a remastered version of "The Good Thymes Virtual Grocery" - original web application created by Thymeleaf authors to show capabilites of template engine.

## Configuration
Module allows to override some Thymeleaf configuration options. You can override default values by specifying them in **application.conf** file. The following configuration options with their defualt values are available:

### thymeleaf.cache.enabled=false
Enables Thymleaf caching capability for templates.

### thymeleaf.cache.ttlms=null
Template cache ttl in milliseconds. If not set, entries would be cached until expelled by LRU.

### thymeleaf.template.mode=XHTML
Defines type of templates that Thymeleaf can process.

### thymeleaf.template.prefix=public/
Defines a folder where you should place your template files.

### thymeleaf.template.suffix=.html
Defines template files extension.

Please refer to Thymeleaf documentation about possible configuration values.

## Usage
The central class of this module is **Thymeleaf** object which has one method with the following signature:
```
def render(templateName: String, templateObjects: Map[String, AnyRef] = Map())
						(implicit language: Lang, flash: Flash = Flash(), session: Session = Session()): Html
```
where 
 - **templateName** is a name of your template. By default module assumes that the templates are located under **public/** directory of Play application which can be overriden by specifying **thymeleaf.template.prefix** property in **application.conf** file.
 - **templateObjects** are a Map of named objects that can be used inside template.

This method also takes some implicit parameters which can be provided by specifying implicit request parameter in controller action method. For example:
```
def ordersList() = Action { implicit request =>
		val orders = Order.findAll()
		Ok(Thymeleaf.render("order/list", Map("orders" -> orders)))
	}
```
### Forms
You can use standard Play Framework **Form** object to map and validate your form data. However, there is a special form helper class **FormWrapper** which can simplify working with forms and provides some methods to get error messages inside your templates. It's advisable always to wrap your form into **FormWrapper** before passing them as a template objects. 
Example:
```
def register() = Action { implicit request =>
		Ok(Thymeleaf.render("register", Map("userForm" -> FormWrapper(registrationForm))))
	}
```
### Accessing template objects
Themeleaf uses OGNL(Object-Graph Navigation Language) as a standard template expression language. OGNL requires classes to have getter methods in order for them to be accessible inside templates. However, you can omit this requirement when using **case** classes which can be accessible without providing getter methods for them.
