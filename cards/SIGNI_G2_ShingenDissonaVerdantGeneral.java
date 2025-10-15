package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_ShingenDissonaVerdantGeneral extends Card {

    public SIGNI_G2_ShingenDissonaVerdantGeneral()
    {
        setImageSets("WXDi-P13-080");

        setOriginalName("翠将　シンゲン//ディソナ");
        setAltNames("スイショウシンゲンディソナ Suishou Shingen Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にあるすべてのシグニが#Sの場合、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Shingen//Dissona, Jade General");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if all the SIGNI on your field are #S, add up to one target SIGNI from your Ener Zone to your hand." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a LRIG this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Shingen//Dissona, Verdant General");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if all of your SIGNI are #S @[Dissona]@ SIGNI, target up to 1 SIGNI from your ener zone, and add it to your hand." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "翠将 武田信玄//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上的全部的精灵是#S的场合，从你的能量区把精灵1张最多作为对象，将其加入手牌。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
