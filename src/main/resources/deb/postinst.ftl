#!/bin/sh
set -e
<% commands.each {command -> %>
<%= command %>
<% } %>
if [ "\$1" = "configure" ]
then
<% dirs.each{ dir -> %>
<% if (dir['owner']) { %>
<% if (dir['group']) { %>
	install -m <%= dir['mode'] %> -o <%= dir['owner'] %> -g <%= dir['group'] %> -d '<%= dir['name'] %>'
<% } else { %>
	install -m <%= dir['mode'] %> -o <%= dir['owner'] %> -d '<%= dir['name'] %>'
<% } %>
<% } else { %>
	install -m <%= dir['mode'] %> -d '<%= dir['name'] %>'
<% } %>
<% } %>

<% files.each{ file -> %>
	chmod <%= file['mode'] %> '<%= file['name'] %>'
<% if (file['owner']) { %>
<% if (file['group']) { %>
	chown <%= file['owner'] %>:<%= file['group'] %> '<%= file['name'] %>'
<% } else { %>
	chown <%= file['owner'] %> '<%= file['name'] %>'
<% } %>
<% } %>
<% } %>
fi
