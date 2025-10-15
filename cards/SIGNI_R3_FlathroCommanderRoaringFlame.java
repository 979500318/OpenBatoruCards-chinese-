package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R3_FlathroCommanderRoaringFlame extends Card {

    public SIGNI_R3_FlathroCommanderRoaringFlame()
    {
        setImageSets("WXDi-P09-040");

        setOriginalName("轟炎　フレイスロ団長");
        setAltNames("ゴウエンフレイスロダンチョウ Gouen Fureisuro Danchou");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札をすべて捨ててもよい。この方法でカードを６枚以上捨てた場合、対戦相手のライフクロス１枚をクラッシュする。\n" +
                "@U：このシグニがアタックしたとき、あなたの手札が０枚の場合、%R %Rを支払ってもよい。そうした場合、ターン終了時まで、このシグニは【アサシン】を得る。"
        );

        setName("en", "Flathro Commander, Roaring Flame");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard your hand. If you discarded six or more cards this way, Crush one of your opponent's Life Cloth.\n" +
                "@U: Whenever this SIGNI attacks, if you have no cards in your hand, you may pay %R %R. If you do, this SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Flathro Commander, Roaring Flame");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may discard all cards from your hand. If you discarded 6 or more cards this way, crush 1 of your opponent's life cloth.\n" +
                "@U: Whenever this SIGNI attacks, if there are 0 cards in your hand, you may pay %R %R. If you do, until end of turn, this SIGNI gains [[Assassin]]."
        );

		setName("zh_simplified", "轰炎 火焰喷射团长");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把手牌全部舍弃。这个方法把牌6张以上舍弃的场合，对战对手的生命护甲1张击溃。\n" +
                "@U :当这只精灵攻击时，你的手牌在0张的场合，可以支付%R %R。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(playerChoiceActivate() && discard(getCardsInHand(getOwner())).size() >= 6)
            {
                crush(getOpponent());
            }
        }

        private void onAutoEff2()
        {
            if(getHandCount(getOwner()) == 0 && payEner(Cost.color(CardColor.RED, 2)))
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
    }
}
