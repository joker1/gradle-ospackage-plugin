#!/bin/sh -e

case "\$1" in
    configure)
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

        <% commands.each {command -> %>
        <%= command %>
        <% } %>
        ;;
esac
