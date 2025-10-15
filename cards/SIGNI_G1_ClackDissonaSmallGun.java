package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G1_ClackDissonaSmallGun extends Card {

    public SIGNI_G1_ClackDissonaSmallGun()
    {
        setImageSets("WXDi-P13-077");

        setOriginalName("小砲　パチン//ディソナ");
        setAltNames("ショウホウパチンディソナ Shouhou Pachin Disona");
        setDescription("jp",
                "@A $T1 %G0：あなたの#Sのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Paching//Dissona, Small Cannon");
        setDescription("en",
                "@A $T1 %G0: Target #S SIGNI on your field gets +5000 power until end of turn." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Clack//Dissona, Small Gun");
        setDescription("en_fan",
                "@A $T1 %G0: Target 1 of your #S @[Dissona]@ SIGNI, and until end of turn, it gets +5000 power." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "小炮 柏青//失调");
        setDescription("zh_simplified", 
                "@A $T1 %G0你的#S的精灵1只作为对象，直到回合结束时为止，其的力量+5000。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().dissona()).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
