module Jekyll
  module FiguresFilter
    def figures(input)
      input.to_s.gsub(/(<figure><img src=['"])\.\/(.*)(['"]\s*\/?>)(<\/figure>)/, '\1rss/\2\3<div style="background-image: url(\2); "></div>\4')
    end

    def figures_atom(input, post_url)
      input.to_s.gsub( /(src=['"])(?![.\/])/, '\1http://tonsky.me' + post_url)
                .gsub( /(src=['"])\.\//,     '\1http://tonsky.me' + post_url + 'rss/')
                .gsub(/(href=["'])\.\//,     '\1http://tonsky.me' + post_url)
                .gsub( /(src=['"])\//,       '\1http://tonsky.me/rss/')
                .gsub(/(href=['"])\//,       '\1http://tonsky.me/')
    end
  end
end

Liquid::Template.register_filter(Jekyll::FiguresFilter)