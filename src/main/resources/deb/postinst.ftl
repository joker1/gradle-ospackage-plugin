#!/bin/sh
set -e
<% commands.each {command -> %>
<%= command %>
<% } %>
if [ "$1" = "configure" ]
then
	<% dirs.each{ dir -> %>
	<% if (dir['owner']) { %>
	<% if (dir['group']) { %>
	install -o <%= dir['owner'] %> -g <%= dir['group'] %> -d <%= dir['name'] %>
	<% } else { %>
	install -o <%= dir['owner'] %> -d <%= dir['name'] %>
	<% } %>
	<% } else { %>
	install -d <%= dir['name'] %>
	<% } %>
	<% } %>
fi
