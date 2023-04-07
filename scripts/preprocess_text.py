# preprocess_text.py
import sys
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.stem import WordNetLemmatizer


# Text preprocessing
def preprocess_text(text):
    # Tokenization
    sentences = sent_tokenize(text)

    # Preprocess each sentence
    preprocessed_sentences = []
    for sentence in sentences:
        words = word_tokenize(sentence)

        # Lowercasing
        words = [word.lower() for word in words]

        # Stopword removal
        stop_words = set(stopwords.words('english'))
        words = [word for word in words if word not in stop_words]

        # Lemmatization
        lemmatizer = WordNetLemmatizer()
        words = [lemmatizer.lemmatize(word) for word in words]

        # Join words to form a preprocessed sentence
        preprocessed_sentence = ' '.join(words)
        preprocessed_sentences.append(preprocessed_sentence)

    return preprocessed_sentences


if __name__ == '__main__':
    extractedText = sys.argv[1]
    preprocessed_words = preprocess_text(extractedText)
    print(preprocessed_words)
