package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G2_ShigureMayoi extends Card {

    public SIGNI_G2_ShigureMayoi()
    {
        setImageSets("WX25-CP1-077");

        setOriginalName("間宵シグレ");
        setAltNames("マヨイシグレ Mayoi Shigure");
        setDescription("jp",
                "@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、あなたの場に他の＜ブルアカ＞のシグニがある場合、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Mayoi Shigure");

        setName("en_fan", "Shigure Mayoi");
        setDescription("en",
                "@U: Whenever this SIGNI banishes a SIGNI in battle, if there is another <<Blue Archive>> SIGNI on your field, target 1 of your opponent's SIGNI with power 3000 or less, and banish it." +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "间宵时雨");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为战斗把精灵1只破坏时，你的场上有其他的<<ブルアカ>>精灵的场合，对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
                banish(target);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
