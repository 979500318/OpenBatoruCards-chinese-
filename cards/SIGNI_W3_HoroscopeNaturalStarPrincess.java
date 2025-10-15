package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W3_HoroscopeNaturalStarPrincess extends Card {

    public SIGNI_W3_HoroscopeNaturalStarPrincess()
    {
        setImageSets("WX24-P3-047");
        setLinkedImageSets("WX24-P3-014");

        setOriginalName("羅星姫　ホロスコープ");
        setAltNames("ラセイキホロスコープ Raseiki Horosukoopu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《占星術の巫女　リメンバ・ドウン》がいる場合、レベル２以上のシグニ１体を対象とし、ターン終了時まで、それのレベルを－１する。\n" +
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。その後、そのカードが＜宇宙＞のシグニの場合、対戦相手のレベル２以下のシグニ１体を対象とし、%W %Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "Horoscope, Natural Star Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, if your LRIG is \"Remember Dawn, Miko of Astrology\", target 1 level 2 or higher SIGNI, and until end of turn, it gets --1 level.\n" +
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a <<Space>> SIGNI, target 1 of your opponent's level 2 or lower SIGNI, and you may pay %W %X. If you do, return it to their hand."
        );

		setName("zh_simplified", "罗星姬 星象图");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《占星術の巫女　リメンバ・ドウン》的场合，等级2以上的精灵1只作为对象，直到回合结束时为止，其的等级-1。\n" +
                "@U :当这只精灵攻击时，你的牌组最上面公开。然后，那张牌是<<宇宙>>精灵的场合，对战对手的等级2以下的精灵1只作为对象，可以支付%W%X。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEffCond1()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("占星術の巫女　リメンバ・ドウン"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).SIGNI().withLevel(2,0)).get();
                gainValue(target, target.getIndexedInstance().getLevel(), -1, ChronoDuration.turnEnd());
            }
        }
        
        private void onAutoEff2()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                if(CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.SPACE))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();

                    if(target != null && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)))
                    {
                        addToHand(target);
                    }
                }
            }
        }
    }
}
