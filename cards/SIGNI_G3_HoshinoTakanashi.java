package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G3_HoshinoTakanashi extends Card {

    public SIGNI_G3_HoshinoTakanashi()
    {
        setImageSets("WXDi-CP02-058");

        setOriginalName("小鳥遊ホシノ");
        setAltNames("タカナシホシノ Takanashi Hoshino");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、【エナチャージ１】をし、あなたのライフクロスが２枚以下の場合、追加で【エナチャージ１】をする。\n" +
                "@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~{{E %X %X %X：対戦相手のパワー8000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Takanashi Hoshino");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all the SIGNI on your field are <<Blue Archive>>, [[Ener Charge 1]], and in addition, if you have two or fewer cards in your Life Cloth, [[Ener Charge 1]].\n@U: Whenever this SIGNI vanishes a SIGNI through battle, vanish target SIGNI on your opponent's field with power 3000 or less.~{{E %X %X %X: Vanish target SIGNI on your opponent's field with power 8000 or more."
        );
        
        setName("en_fan", "Hoshino Takanashi");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if all of your SIGNI are <<Blue Archive>> SIGNI, [[Ener Charge 1]], and if your life cloth is 2 or less, additionally [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI banishes a SIGNI in battle, target 1 of your opponent's SIGNI with power 3000 or less, and banish it." +
                "~{{E %X %X %X: Target 1 of your opponent's SIGNI with power 8000 or more, and banish it."
        );

		setName("zh_simplified", "小鸟游星野");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵是<<ブルアカ>>的场合，[[能量填充1]]，你的生命护甲在2张以下的场合，追加[[能量填充1]]。\n" +
                "@U :当这只精灵因为战斗把精灵1只破坏时，对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n" +
                "~{{E%X %X %X:对战对手的力量8000以上的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            EnterAbility enter = registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                enerCharge(1);
                
                if(getLifeClothCount(getOwner()) <= 2)
                {
                    enerCharge(1);
                }
            }
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(8000,0)).get();
            banish(target);
        }
    }
}
