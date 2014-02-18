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
	install -m <%= dir['mode'] %> -o <%= dir['owner'] %> -g <%= dir['group'] %> -d <%= dir['name'] %>
<% } else { %>
	install -m <%= dir['mode'] %> -o <%= dir['owner'] %> -d <%= dir['name'] %>
<% } %>
<% } else { %>
	install -m <%= dir['mode'] %> -d <%= dir['name'] %>
<% } %>
<% } %>
fi
