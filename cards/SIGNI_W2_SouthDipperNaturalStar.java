package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W2_SouthDipperNaturalStar extends Card {

    public SIGNI_W2_SouthDipperNaturalStar()
    {
        setImageSets("WX24-P3-062");

        setOriginalName("羅星　ナントロク");
        setAltNames("ラセイナントロク Rasei Nantoroku");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの一番上を公開する。その後、そのカードがレベル１のシグニの場合、対戦相手のレベル１のシグニ１体を対象とし、あなたのエナゾーンから＜宇宙＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "South Dipper, Natural Star");
        setDescription("en",
                "@U: At the beginning of your attack phase, reveal the top card of your deck. If it is a level 1 SIGNI, target 1 of your opponent's level 1 SIGNI, and you may put 1 <<Space>> SIGNI from your ener zone into the trash. If you do, return it to their hand."
        );

		setName("zh_simplified", "罗星 南斗六星");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的牌组最上面公开。然后，那张牌是等级1的精灵的场合，对战对手的等级1的精灵1只作为对象，可以从你的能量区把<<宇宙>>精灵1张放置到废弃区。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
                
                if(cardIndex.getIndexedInstance().getLevelByRef() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
                    
                    if(target != null)
                    {
                        CardIndex cardIndexEner = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.SPACE).fromEner()).get();
                        
                        if(cardIndexEner != null)
                        {
                            trash(cardIndexEner);
                            
                            addToHand(target);
                        }
                    }
                }
            }
        }
    }
}
