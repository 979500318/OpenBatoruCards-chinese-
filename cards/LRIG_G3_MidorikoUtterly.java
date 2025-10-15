package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_G3_MidorikoUtterly extends Card {

    public LRIG_G3_MidorikoUtterly()
    {
        setImageSets("WXDi-P13-009", "WXDi-P13-009U");

        setOriginalName("散散　緑姫");
        setAltNames("サンサンミドリコ San San Midoriko");
        setDescription("jp",
                "@C：あなたの中央のシグニゾーンにある#Sのシグニのパワーを＋5000する。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に#Sのシグニが２体以上ある場合、【エナチャージ１】をし、その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。\n" +
                "@A @[エクシード４]@：次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋10000する。"
        );

        setName("en", "Midoriko, Dispersal Three");
        setDescription("en",
                "@C: #S SIGNI in your center SIGNI Zone get +5000 power.\n@U: At the beginning of your attack phase, if there are two or more #S SIGNI on your field, [[Ener Charge 1]], then, add up to one target SIGNI from your Ener Zone to your hand.\n@A @[Exceed 4]@: All SIGNI on your field get +10000 power until the end of your opponent's next end phase. "
        );
        
        setName("en_fan", "Midoriko, Utterly");
        setDescription("en_fan",
                "@C: #S @[Dissona]@ SIGNI in your center SIGNI zone get +5000 power.\n" +
                "@U: At the beginning of your attack phase, if you have 2 or more #S @[Dissona]@ SIGNI, [[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@A @[Exceed 4]@: Until the end of your opponent's next turn, all of your SIGNI get +10000 power."
        );

		setName("zh_simplified", "散散 绿姬");
        setDescription("zh_simplified", 
                "@C 你的中央的精灵区的#S的精灵的力量+5000。\n" +
                "@U 你的攻击阶段开始时，你的场上的#S的精灵在2只以上的场合，[[能量填充1]]，然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n" +
                "@A @[超越 4]@:直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+10000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().SIGNI().dissona().fromLocation(CardLocation.SIGNI_CENTER), new PowerModifier(5000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerActionAbility(new ExceedCost(4), this::onActionEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() >= 2)
            {
                enerCharge(1);
                
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            }
        }

        private void onActionEff()
        {
            gainPower(getSIGNIOnField(getOwner()), 10000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}

