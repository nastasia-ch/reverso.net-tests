package mailru.nastasiachernega.tests.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CorrectionUtilsOfHtmlElements {

    public String getTagName(Element tagElement) {
        return tagElement.tagName();
    };

    public String getTagAttributes(Element tagElement) {
        return tagElement.attributes().toString();
    };

    public String getOpeningTag(String tagName, String attributes) {
        return "<"+tagName + attributes+">";
    };

    public String getClosingTag(String tagName) {
        return "</"+tagName+">";
    };

    public String clearHtmlElementFromOpenOrCloseTag(String htmlElement, String tag) {

        int beginIndex = htmlElement.indexOf(tag);
        int endIndex = beginIndex+tag.length();

        return new StringBuffer(htmlElement)
                .delete(beginIndex,endIndex).toString();

    }

    public String clearElementFromTagSpan(String htmlElement, Element tagElement) {

        String openingTag = getOpeningTag(getTagName(tagElement),
                                          getTagAttributes(tagElement));

        String contextWithoutOpeningTag =
                clearHtmlElementFromOpenOrCloseTag(htmlElement,openingTag);

        String closingTag = getClosingTag(getTagName(tagElement));

        return clearHtmlElementFromOpenOrCloseTag(contextWithoutOpeningTag,closingTag).substring(1);

    }

    public String clearElementFromSpanAndATags(String htmlElement,
                                               Element firstTagElement,
                                               Elements secondTagElement) {

        String firstOpeningTag = getOpeningTag(getTagName(firstTagElement),
                getTagAttributes(firstTagElement));

        String contextWithoutFirstOpeningTag =
                clearHtmlElementFromOpenOrCloseTag(htmlElement,firstOpeningTag);

        String firstClosingTag = getClosingTag(getTagName(firstTagElement));

        String contextWithoutFirstClosingTag =
                clearHtmlElementFromOpenOrCloseTag(contextWithoutFirstOpeningTag,firstClosingTag);

        for (Element element : secondTagElement) {

            String secondOpeningTag = getOpeningTag(getTagName(element),
                    getTagAttributes(element));

            String contextWithoutSecondOpeningTag =
                    clearHtmlElementFromOpenOrCloseTag(contextWithoutFirstClosingTag, secondOpeningTag);

            contextWithoutFirstClosingTag = contextWithoutSecondOpeningTag;

            String secondClosingTag = getClosingTag(getTagName(element));

            String contextWithoutSecondClosingTag =
                    clearHtmlElementFromOpenOrCloseTag(contextWithoutFirstClosingTag, secondClosingTag);

            contextWithoutFirstClosingTag = contextWithoutSecondClosingTag;

        }
        return contextWithoutFirstClosingTag.substring(1);

    }

}
