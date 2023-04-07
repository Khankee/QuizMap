from rake_nltk import Rake
import sys


def extract_keyphrases(text_for_phrases):
    rake = Rake()
    rake.extract_keywords_from_text(text_for_phrases)
    key_phrases = rake.get_ranked_phrases()
    return key_phrases


if __name__ == '__main__':
    text = sys.argv[1]
    # text = "Natural language processing is a subfield of linguistics, computer science, and artificial intelligence" \
    #   "concerned with the interactions between computers and human language, in particular how to program computers" \
    #   "to process and analyze large amounts of natural language data."
    keyphrases = extract_keyphrases(text)
    print(keyphrases)
