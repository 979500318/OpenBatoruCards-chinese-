package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_W1_NovaFessoneNaturalStar extends Card {

    public SIGNI_W1_NovaFessoneNaturalStar()
    {
        setImageSets("WXDi-P16-060", Mask.IGNORE+"SPDi10-20");

        setOriginalName("羅星　ノヴァ//フェゾーネ");
        setAltNames("ラセイノヴァフェゾーネ Rasei Nova Fezoon");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのデッキの上からカードを４枚見る。その中から#Gを持つシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Nova//Fesonne, Natural Planet");
        setDescription("en",
                "@U: At the end of your turn, look at the top four cards of your deck. Reveal a SIGNI with a #G from among them and add it to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Nova//Fessone, Natural Star");
        setDescription("en_fan",
                "@U: At the end of your turn, look at the top 4 cards of your deck. Reveal 1 #G @[Guard]@ SIGNI from among them, add it to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "罗星 超//音乐节");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，从你的牌组上面看4张牌。从中把持有#G的精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            look(4);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
