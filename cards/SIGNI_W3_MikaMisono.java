package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;

public final class SIGNI_W3_MikaMisono extends Card {

    public SIGNI_W3_MikaMisono()
    {
        setImageSets("WXDi-CP02-052");

        setOriginalName("聖園ミカ");
        setAltNames("ミソノミカ Misono Mika");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、対戦相手のパワー8000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のライフクロスが３枚以下の場合、それを手札に戻す。\n" +
                "$$2対戦相手のライフクロスが４枚以上ある場合、それをトラッシュに置く。" +
                "~{{E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。"
        );

        setName("en", "Misono Mika");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are <<Blue Archive>>, choose target SIGNI on your opponent's field with power 8000 or less and you may discard a card. If you do, choose one of the following.\n$$1If your opponent has three or fewer cards in their Life Cloth, return it to its owner's hand.\n$$2If your opponent has four or more cards in their Life Cloth, put it into its owner's trash.~{{E: Target SIGNI on your opponent's field loses its abilities until end of turn."
        );
        
        setName("en_fan", "Mika Misono");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, target 1 of your opponent's SIGNI with power 8000 or less, and you may discard 1 card from your hand. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If your opponent has 3 or less life cloth, return it to their hand.\n" +
                "$$2 If your opponent has 4 or more life cloth, put it into the trash." +
                "~{{E: Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities."
        );

		setName("zh_simplified", "圣园弥香");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，对战对手的力量8000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的生命护甲在3张以下的场合，将其返回手牌。\n" +
                "$$2 对战对手的生命护甲在4张以上的场合，将其放置到废弃区。\n" +
                "~{{E:对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

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

            EnterAbility enter = registerEnterAbility(this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI().withPower(0,8000)).get();
                
                if(target != null && discard(0,1).get() != null)
                {
                    if(playerChoiceMode() == 1)
                    {
                        if(getLifeClothCount(getOpponent()) <= 3)
                        {
                            addToHand(target);
                        }
                    } else if(getLifeClothCount(getOpponent()) >= 4)
                    {
                        trash(target);
                    }
                }
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
