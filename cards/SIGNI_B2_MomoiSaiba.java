package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B2_MomoiSaiba extends Card {

    public SIGNI_B2_MomoiSaiba()
    {
        setImageSets("WXDi-CP02-080");

        setOriginalName("才羽モモイ");
        setAltNames("サイバモモイ Saiba Momoi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、手札を１枚捨ててもよい。そうした場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手は手札を１枚捨てる。\n" +
                "$$2あなたの場に《才羽ミドリ》がある場合、対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Saiba Momoi");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are <<Blue Archive>>, you may discard a card. If you do, choose one of the following.\n$$1Your opponent discards a card. \n$$2If there is \"Saiba Midori\" on your field, your opponent discards a card at random.~{{C: This SIGNI gets +4000 power.@@" +
                "~#Down target SIGNI on your opponent's field and freeze it. Your opponent discards a card."
        );
        
        setName("en_fan", "Momoi Saiba");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, you may discard 1 card from your hand. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Your opponent discards 1 card from their hand.\n" +
                "$$2 If there is \"Midori Saiba\" on your field, choose 1 card from your opponent's hand without looking, and your opponent discards it." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "才羽桃");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，可以把手牌1张舍弃。这样做的场合，从以下的2种选1种。\n" +
                "$$1 对战对手把手牌1张舍弃。\n" +
                "$$2 你的场上有《才羽ミドリ》的场合，不看对战对手的手牌选1张，舍弃。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

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

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0 &&
               discard(0,1).get() != null)
            {
                if(playerChoiceMode() == 1)
                {
                    discard(getOpponent(), 1);
                } else if(new TargetFilter().own().SIGNI().withName("才羽ミドリ").getValidTargetsCount() > 0)
                {
                    CardIndex cardIndex = playerChoiceHand().get();
                    discard(cardIndex);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            discard(getOpponent(), 1);
        }
    }
}
