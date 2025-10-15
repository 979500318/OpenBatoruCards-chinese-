package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class LRIG_W2_SasheCraftEternalMessenger extends Card {

    public LRIG_W2_SasheCraftEternalMessenger()
    {
        setImageSets("WXDi-P11-013", "WX25-P2-013");
        setLinkedImageSets("WXDi-P11-TK01", "WXDi-P11-TK02");

        setOriginalName("悠久の使者　サシェ・クラフト");
        setAltNames("ユウキュウノシシャサシェクラフト Yuukyuu no Shisha Sashe Kurafuto");
        setDescription("jp",
                "@E：《白羅星姫　サタン》１枚と《白羅星姫　フルムーン》１枚を公開する。それらのどちらか１枚を対戦相手に見せずに裏向きでルリグデッキに加える。"
        );

        setName("en", "Sashe Craft, Eternal Emissary");
        setDescription("en",
                "@E: Reveal a \"Saturn, White Planet Queen\" and a \"Full Moon, White Planet Queen\". Add one of them to your LRIG Deck face down without showing it to your opponent. "
        );
        
        setName("en_fan", "Sashe Craft, Eternal Messenger");
        setDescription("en_fan",
                "@E: Reveal 1 \"Saturn, White Natural Star Princess\" and 1 \"Full Moon, White Natural Star Princess\". Add 1 of them into your LRIG deck face-down without showing it to your opponent."
        );

		setName("zh_simplified", "悠久的使者 莎榭·衍生");
        setDescription("zh_simplified", 
                "@E :《白羅星姫　サタン》1张和《白羅星姫　フルムーン》1张公开。这些的其中1张对战对手不看，里向加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            look(craft(getLinkedImageSets().get(0)));
            look(craft(getLinkedImageSets().get(1)));
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().Resona().fromLooked()).get();
            returnToDeck(cardIndex, DeckPosition.TOP);
            
            exclude(getCardsInLooked(getOwner()));
        }
    }
}
