package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SIGNI_G1_CapricornNaturalStar extends Card {
    
    public SIGNI_G1_CapricornNaturalStar()
    {
        setImageSets("WXDi-P06-071");
        
        setOriginalName("羅星　カプリコーン");
        setAltNames("ラセイカプリコーン Rasei Kapurikoon");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開し、そのカードをデッキの一番下に置いてもよい。この効果で公開されたカードがレベル１のシグニの場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋4000する。"
        );
        
        setName("en", "Capricorn, Natural Planet");
        setDescription("en",
                "@E: Reveal the top card of your deck and you may put that card on the bottom of your deck. If the card revealed with this effect is a level one SIGNI, this SIGNI gets +4000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Capricorn, Natural Star");
        setDescription("en_fan",
                "@E: Reveal the top card of your deck and you may put it on the bottom of your deck. If the card revealed by this effect was a level 1 SIGNI, until the end of your opponent's next turn, this SIGNI gets +4000 power."
        );
        
		setName("zh_simplified", "罗星 摩羯座");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开，可以把那张牌放置到牌组最下面。这个效果公开的牌是等级1的精灵的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);
        
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
            CardIndex cardIndex = reveal();

            if(cardIndex != null)
            {
                boolean match = cardIndex.getIndexedInstance().getLevelByRef() == 1;

                returnToDeck(cardIndex, playerChoiceAction(ActionHint.TOP, ActionHint.BOTTOM) == 1 ? DeckPosition.TOP : DeckPosition.BOTTOM);

                if(match)
                {
                    gainPower(getCardIndex(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
    }
}
