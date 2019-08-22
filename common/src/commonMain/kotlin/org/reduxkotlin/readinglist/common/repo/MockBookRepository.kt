package org.reduxkotlin.readinglist.common.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json


class MockRepositoryFactory {

    fun success(): BookRepository {
        return object : BookRepository {
            override suspend fun search(query: String): GatewayResponse<List<Book>, GenericError> {
                return GatewayResponse.createSuccess(getValidResponse(), 200, "Successful mock Respoonse")
            }
        }
    }

    fun delayedLoading(delayInMs: Long): BookRepository {
        return object : BookRepository {
            override suspend fun search(query: String): GatewayResponse<List<Book>, GenericError> {
                return withContext(Dispatchers.Default) {
                    delay(delayInMs)
                    GatewayResponse.createSuccess<List<Book>, GenericError>(getValidResponse(), 200, "Successful mock response")
                }
            }
        }
    }

    fun error(): BookRepository {
        return object : BookRepository {
            override suspend fun search(query: String): GatewayResponse<List<Book>, GenericError> {
                return GatewayResponse.createError(GenericError("Mock error from server"), 500, "Mock error from server")
            }
        }
    }


    companion object {
        fun getValidResponse(): List<Book> {
            val response = Json.nonstrict.parse(BooksResponse.serializer(), VALID_RESPONSE_JSON)
            return response.docs
        }


        const val VALID_RESPONSE_JSON = """{
 "start": 0,
 "num_found": 5,
 "numFound": 5,
 "docs": [
  {
   "title_suggest": "Cracking The Coding Interview",
   "edition_key": [
    "OL25442597M"
   ],
   "cover_i": 7276811,
   "isbn": [
    "098478280X",
    "978-0984782802"
   ],
   "has_fulltext": false,
   "text": [
    "OL25442597M",
    "098478280X",
    "978-0984782802",
    "Gayle  Laakmann McDowell",
    "OL7031499A",
    "Cracking The Coding Interview",
    "/works/OL16816263W",
    "CarrerCup, LLC"
   ],
   "author_name": [
    "Gayle  Laakmann McDowell"
   ],
   "seed": [
    "/books/OL25442597M",
    "/works/OL16816263W",
    "/authors/OL7031499A"
   ],
   "author_key": [
    "OL7031499A"
   ],
   "title": "Cracking The Coding Interview",
   "publish_date": [
    "2012"
   ],
   "type": "work",
   "ebook_count_i": 0,
   "publish_place": [
    "Palo Alto, CA, USA"
   ],
   "edition_count": 1,
   "key": "/works/OL16816263W",
   "publisher": [
    "CarrerCup, LLC"
   ],
   "language": [
    "eng"
   ],
   "last_modified_i": 1398745799,
   "cover_edition_key": "OL25442597M",
   "publish_year": [
    2012
   ],
   "first_publish_year": 2012
  },
  {
   "title_suggest": "Cracking the coding interview",
   "edition_key": [
    "OL25536640M"
   ],
   "isbn": [
    "145157827X",
    "9781451578270"
   ],
   "has_fulltext": true,
   "text": [
    "OL25536640M",
    "145157827X",
    "9781451578270",
    "Gayle Laakmann",
    "732254141",
    "crackingcodingin00laak_393",
    "OL7200476A",
    "Problems, exercises",
    "Vocational guidance",
    "Accessible book",
    "Computer programming",
    "Protected DAISY",
    "Computer programmers",
    "Employment interviewing",
    "Cracking the coding interview",
    "/works/OL16919971W",
    "Gayle Laakmann",
    "CarrerCup"
   ],
   "author_name": [
    "Gayle Laakmann"
   ],
   "seed": [
    "/books/OL25536640M",
    "/works/OL16919971W",
    "/subjects/computer_programmers",
    "/subjects/problems_exercises",
    "/subjects/computer_programming",
    "/subjects/vocational_guidance",
    "/subjects/employment_interviewing",
    "/authors/OL7200476A"
   ],
   "oclc": [
    "732254141"
   ],
   "ia": [
    "crackingcodingin00laak_393"
   ],
   "author_key": [
    "OL7200476A"
   ],
   "subject": [
    "Problems, exercises",
    "Vocational guidance",
    "Accessible book",
    "Computer programming",
    "Protected DAISY",
    "Computer programmers",
    "Employment interviewing"
   ],
   "title": "Cracking the coding interview",
   "ia_collection_s": "printdisabled;librarygenesis",
   "printdisabled_s": "OL25536640M",
   "type": "work",
   "ebook_count_i": 1,
   "publish_place": [
    "Seattle, WA"
   ],
   "edition_count": 1,
   "key": "/works/OL16919971W",
   "public_scan_b": false,
   "publisher": [
    "CarrerCup"
   ],
   "language": [
    "eng"
   ],
   "last_modified_i": 1406674841,
   "first_publish_year": 2010,
   "publish_year": [
    2010
   ],
   "publish_date": [
    "2010"
   ]
  },
  {
   "title_suggest": "Cracking the Coding Interview",
   "publisher": [
    "CAREERCUP"
   ],
   "subtitle": "189 Programming Questions and Solutions",
   "has_fulltext": false,
   "title": "Cracking the Coding Interview",
   "edition_key": [
    "OL26395119M"
   ],
   "last_modified_i": 1510159198,
   "edition_count": 1,
   "isbn": [
    "9780984782857",
    "0984782850"
   ],
   "author_name": [
    "Gayle  Laakmann McDowell"
   ],
   "cover_edition_key": "OL26395119M",
   "seed": [
    "/books/OL26395119M",
    "/works/OL17805397W",
    "/authors/OL7031499A"
   ],
   "first_publish_year": 2015,
   "publish_year": [
    2015
   ],
   "key": "/works/OL17805397W",
   "text": [
    "OL26395119M",
    "189 Programming Questions and Solutions",
    "Gayle  Laakmann McDowell",
    "9780984782857",
    "0984782850",
    "OL7031499A",
    "Cracking the Coding Interview",
    "/works/OL17805397W",
    "CAREERCUP",
    "Cracking the Coding Interview: 189 Programming Questions and Solutions"
   ],
   "publish_date": [
    "07/01/2015"
   ],
   "author_key": [
    "OL7031499A"
   ],
   "type": "work",
   "ebook_count_i": 0
  },
  {
   "title_suggest": "Cracking the intuition code",
   "edition_key": [
    "OL371771M",
    "OL7975898M",
    "OL24293823M"
   ],
   "cover_i": 593890,
   "isbn": [
    "0809228394",
    "0809228386",
    "9780809228393",
    "0071394419",
    "9780809228386",
    "9780071394413"
   ],
   "has_fulltext": false,
   "text": [
    "OL371771M",
    "OL7975898M",
    "OL24293823M",
    "0809228394",
    "0809228386",
    "9780809228393",
    "0071394419",
    "9780809228386",
    "9780071394413",
    "Gail Ferguson",
    "44737008",
    "OL232286A",
    "Self-Improvement",
    "Intuition",
    "OverDrive",
    "Nonfiction",
    "Internet Archive Wishlist",
    "Understanding and Mastering Your Intuitive Power",
    "understanding and mastering  your intuitive power",
    "Cracking the intuition code",
    "/works/OL1935921W",
    "Gail Ferguson.",
    "McGraw-Hill",
    "Contemporary Books",
    "Cracking the Intuition Code",
    "Cracking the Intuition Code ",
    "98033368",
    "THREE MONTHS after the Oklahoma City bombing on April 19, 1995, which killed 168 people and wounded scores of others, CNN aired an interview with one of the victims, a woman who was learning to walk on an artificial leg."
   ],
   "author_name": [
    "Gail Ferguson"
   ],
   "seed": [
    "/books/OL371771M",
    "/books/OL7975898M",
    "/books/OL24293823M",
    "/works/OL1935921W",
    "/subjects/internet_archive_wishlist",
    "/subjects/intuition",
    "/subjects/overdrive",
    "/subjects/nonfiction",
    "/subjects/self-improvement",
    "/authors/OL232286A"
   ],
   "oclc": [
    "44737008"
   ],
   "author_key": [
    "OL232286A"
   ],
   "subject": [
    "Self-Improvement",
    "Intuition",
    "OverDrive",
    "Nonfiction",
    "Internet Archive Wishlist"
   ],
   "title": "Cracking the intuition code",
   "publish_date": [
    "1999",
    "2002",
    "May 11, 2000"
   ],
   "type": "work",
   "ebook_count_i": 0,
   "publish_place": [
    "Lincolnwood, Ill",
    "New York"
   ],
   "edition_count": 3,
   "key": "/works/OL1935921W",
   "id_goodreads": [
    "2564127"
   ],
   "id_overdrive": [
    "96D0F9CD-AD07-4E00-AA8E-AE8F66025287"
   ],
   "publisher": [
    "McGraw-Hill",
    "Contemporary Books"
   ],
   "language": [
    "eng"
   ],
   "lccn": [
    "98033368"
   ],
   "last_modified_i": 1553918905,
   "id_librarything": [
    "577887"
   ],
   "cover_edition_key": "OL7975898M",
   "first_sentence": [
    "THREE MONTHS after the Oklahoma City bombing on April 19, 1995, which killed 168 people and wounded scores of others, CNN aired an interview with one of the victims, a woman who was learning to walk on an artificial leg."
   ],
   "publish_year": [
    1999,
    2002,
    2000
   ],
   "first_publish_year": 1999
  },
  {
   "title_suggest": "Cracking the corporate code",
   "edition_key": [
    "OL8043018M"
   ],
   "isbn": [
    "0814407714",
    "9780814407714"
   ],
   "has_fulltext": true,
   "text": [
    "OL8043018M",
    "0814407714",
    "9780814407714",
    "crackingcorporat00cobb",
    "In library",
    "African American executives",
    "Success in business",
    "Accessible book",
    "Case studies",
    "Protected DAISY",
    "Interviews",
    "The Revealing Success Stories of 32 African-American Executives",
    "Cracking the corporate code",
    "/works/OL16936907W",
    "AMACOM/American Management Association",
    "Cracking the Corporate Code",
    "WHEN black men and women enter the world of corporate America, conflicting feelings are their constant companions."
   ],
   "seed": [
    "/books/OL8043018M",
    "/works/OL16936907W",
    "/subjects/interviews",
    "/subjects/case_studies",
    "/subjects/african_american_executives",
    "/subjects/in_library",
    "/subjects/success_in_business"
   ],
   "ia": [
    "crackingcorporat00cobb"
   ],
   "subject": [
    "In library",
    "African American executives",
    "Success in business",
    "Accessible book",
    "Case studies",
    "Protected DAISY",
    "Interviews"
   ],
   "title": "Cracking the corporate code",
   "lending_identifier_s": "crackingcorporat00cobb",
   "ia_collection_s": "printdisabled;inlibrary;browserlending;americana;internetarchivebooks",
   "printdisabled_s": "OL8043018M",
   "type": "work",
   "ebook_count_i": 1,
   "ia_box_id": [
    "IA128502"
   ],
   "edition_count": 1,
   "first_publish_year": 2003,
   "key": "/works/OL16936907W",
   "id_goodreads": [
    "963056"
   ],
   "public_scan_b": false,
   "publisher": [
    "AMACOM/American Management Association"
   ],
   "language": [
    "eng"
   ],
   "last_modified_i": 1500717983,
   "lending_edition_s": "OL8043018M",
   "id_librarything": [
    "2883988"
   ],
   "cover_edition_key": "OL8043018M",
   "first_sentence": [
    "WHEN black men and women enter the world of corporate America, conflicting feelings are their constant companions."
   ],
   "publish_year": [
    2003
   ],
   "publish_date": [
    "March 2003"
   ]
  }
 ]
}"""


    }
}


