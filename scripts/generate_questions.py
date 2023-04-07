import spacy
import sys

# Load the English language model
nlp = spacy.load("en_core_web_sm")


def generate_questions(raw_text):
    # Process the text using spaCy
    doc = nlp(raw_text)
    questions = []

    # Iterate over the named entities in the text
    for ent in doc.ents:
        # Generate questions based on the type of named entity
        if ent.label_ in ['PERSON', 'ORG']:
            questions.append(f"Who is {ent.text}?")
        elif ent.label_ in ['DATE', 'TIME']:
            questions.append(f"When did {ent.text} happen?")
        elif ent.label_ in ['GPE', 'LOC']:
            questions.append(f"Where is {ent.text}?")

    # Iterate over the noun chunks in the text
    for chunk in doc.noun_chunks:
        # Generate questions for noun chunks with a subject dependency
        if chunk.root.dep_ == 'nsubj':
            questions.append(f"What is {chunk.text}?")

    # Iterate over the tokens in the text
    for token in doc:
        # Generate questions for root verbs
        if token.dep_ == 'ROOT' and token.tag_ == 'VB':
            questions.append(f"Why did {doc[token.i - 1].text} {token.text}?")

    return questions


if __name__ == '__main__':
    text = sys.argv[1]
    # Sample text
    text1 = "Barack Obama was the 44th President of the United States, born in Honolulu, Hawaii. He was elected in " \
            "2008 and served two terms. He was succeeded by Donald Trump in 2017. Obama studied law at Harvard " \
            "University and taught constitutional law at the University of Chicago Law School."
    # Generate questions from the text
    questions = generate_questions(text)
    for question in questions:
        print(question)