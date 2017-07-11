<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container">
		<h1>Preferences</h1>
    	<div class="row">
    		<div class="col-md-6">
		    	<div class="panel panel-default">
		    		<div class="panel-heading">Project</div>
		    		<div class="panel-body">
						<div class="row">
							<div class="col-md-4"><strong>Project Information</strong></div>
							<div class="col-md-8">${projectInfo.name}</div>
						</div>
						<div class="row">
							<div class="col-md-4"><strong>Version</strong></div>
							<div class="col-md-8">${projectInfo.version}</div>
						</div>
						<div class="row">
							<div class="col-md-4"><strong># Categories</strong></div>
							<div class="col-md-8">${categories.numberOfCategories}</div>
						</div>
						<div class="row">
							<div class="col-md-4"><strong># Preferences</strong></div>
							<div class="col-md-8">${categories.numberOfPreferences}</div>
						</div>
		    		</div>
		    	</div>
    		</div>
    		<div class="col-md-6">
		    	<div class="panel panel-default">
		    		<div class="panel-heading">Document Information</div>
		    		<div class="panel-body">
		    			<#list information.filenames as filename>
						<div class="row">
							<div class="col-md-4"><#if information.filenames?first == filename><strong>Filename<#if information.filenames?size gt 1>s <span class="badge">${information.filenames?size}</span></#if></strong></#if></div>
							<div class="col-md-8">${filename}</div>
						</div>
						</#list>
						<div class="row">
							<div class="col-md-4"><strong>Date</strong></div>
							<div class="col-md-8">${information.currentDate?string('dd.MM.yyyy HH:mm:ss')}</div>
						</div>
		    		</div>
		    	</div>
    		</div>
    	</div>

		<ul class="nav nav-pills nav-stacked col-md-4" role="tablist">
			<#list categories.values as category>
			<li role="presentation"><a href="#category${category?counter}" aria-controls="category${category?counter}" role="tab" data-toggle="tab">${category.name}<span class="badge">${category.preferences?size}</span></a></li>
			</#list>
		</ul>
		<div class="tab-content col-md-8">
			<#list categories.values as category>
			<div role="tabpanel" class="tab-pane" id="category${category?counter}">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4"><strong>Description</strong></div>
							<div class="col-md-8">${category.description}</div>
						</div>
						<div class="row">
							<div class="col-md-4"><strong># Preferences</strong></div>
							<div class="col-md-8">${category.preferences?size}</div>
						</div>
					</div>
				</div>
				<#list category.preferences as preference>
				<div class="panel panel-default">
					<div class="panel-heading">${preference.name}</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-3"><strong>Defined in</strong></div>
							<div class="col-md-9">${preference.definitionLocation}</div>
						</div>
						<div class="row">
							<div class="col-md-3"><strong>Description</strong></div>
							<div class="col-md-9">${preference.description}</div>
						</div>
						<div class="row">
							<div class="col-md-3"><strong>Has multiple values</strong></div>
							<div class="col-md-9">${preference.multivalue?c}</div>
						</div>
						<div class="row">
							<div class="col-md-3"><strong>Is disabled</strong></div>
							<div class="col-md-9">${preference.disabled?c}</div>
						</div>
						<div class="row">
							<div class="col-md-3"><strong>Is Environment Variable enabled</strong></div>
							<div class="col-md-9">${preference.environmentEnabled?c}</div>
						</div>
						<div class="row">
							<div class="col-md-3"><strong>Type</strong></div>
							<div class="col-md-9">${preference.type.value}</div>
						</div>
						<div class="row">
							<div class="col-md-3"><strong>Protection Scope</strong></div>
							<#assign defaultStyle="col-md-9"/>
							<#assign isProtectionScopeNotDefined = preference.protectionScope!"null"/>
							<#if isProtectionScopeNotDefined=="null">
							<#assign style="${defaultStyle} text-danger"/>
							<#assign protectionScopeText="-- N/A --"/>
							<#else>
							<#assign style="${defaultStyle}">
							<#assign protectionScopeText="${preference.protectionScope.value}">
							</#if>
							<div class="${style}">${protectionScopeText}</div>
						</div>
						<#if preference.multivalue && preference.values??>
						<#list preference.values as value>
						<div class="row">
							<div class="col-md-3"><#if preference.values[0] == value><strong>Values</strong> <span class="badge">${preference.values?size}</span></#if></div>
							<div class="col-md-9">${value}</div>
						</div>
						</#list>
						<#else>
						<div class="row">
							<div class="col-md-3"><strong>Value</strong></div>
							<#if preference.values??>
							<div class="col-md-9">${(preference.values)?first!"None"}</div>
							<#else>
							<div class="col-md-9">None</div>
							</#if>
						</div>
						</#if>
					</div>
				</div>
				</#list>
			</div>
			</#list>
		</div>
    </div>
</body>
</html>
