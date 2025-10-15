package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_HawkingNaturalStar extends Card {

    public SIGNI_R2_HawkingNaturalStar()
    {
        setImageSets("WXDi-P15-089");

        setOriginalName("羅星　ホーキング");
        setAltNames("ラセイホーキング Rasei Hookingu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上を公開する。その後、そのカードがレベル１のシグニの場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。" +
                "~#：手札を１枚捨て、カードを３枚引く。"
        );

        setName("en", "Hawking, Natural Planet");
        setDescription("en",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. Then, if that card is a level one SIGNI, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash." +
                "~#Discard a card and draw three cards. "
        );
        
        setName("en_fan", "Hawking, Natural Star");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If it is a level 1 SIGNI, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash." +
                "~#Discard 1 card from your hand, and draw 3 cards."
        );

		setName("zh_simplified", "罗星 霍金辐射");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的牌组最上面公开。然后，那张牌是等级1的精灵的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。" +
                "~#手牌1张舍弃，抽3张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getLevelByRef() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
                trash(target);
            }
            
            returnToDeck(cardIndex, DeckPosition.TOP);
        }

        private void onLifeBurstEff()
        {
            discard(1);
            draw(3);
        }
    }
}
