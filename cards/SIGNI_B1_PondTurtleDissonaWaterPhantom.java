package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B1_PondTurtleDissonaWaterPhantom extends Card {

    public SIGNI_B1_PondTurtleDissonaWaterPhantom()
    {
        setImageSets("WXDi-P12-072");

        setOriginalName("幻水　イシガメ//ディソナ");
        setAltNames("ゲンスイイシガメディソナ Gensui Ishigame Disona");
        setDescription("jp",
                "@C：あなたの手札が対戦相手より２枚以上多いかぎり、このシグニのパワーは＋2000される。\n" +
                "@U：あなたのアタックフェイズ開始時、手札から#Sのカードを１枚捨ててもよい。そうした場合、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Pond Turtle//Dissona, Phantom Beast");
        setDescription("en",
                "@C: As long as you have two or more cards in your hand than your opponent, this SIGNI gets +2000 power.\n@U: At the beginning of your attack phase, you may discard a #S card. If you do, your opponent discards a card."
        );
        
        setName("en_fan", "Pond Turtle//Dissona, Water Phantom");
        setDescription("en_fan",
                "@C: If there are at least 2 more cards in your hand than in your opponent's, this SIGNI gets +2000 power.\n" +
                "@U: At the beginning of your attack phase, you may discard 1 #S @[Dissona]@ card from your hand. If you do, your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "幻水 石龟//失调");
        setDescription("zh_simplified", 
                "@C :你的手牌比对战对手多2张以上时，这只精灵的力量+2000。\n" +
                "@U 你的攻击阶段开始时，可以从手牌把#S的牌1张舍弃。这样做的场合，对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) - getHandCount(getOpponent()) >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1, new TargetFilter().dissona()).get() != null)
            {
                discard(getOpponent(), 1);
            }
        }
    }
}
