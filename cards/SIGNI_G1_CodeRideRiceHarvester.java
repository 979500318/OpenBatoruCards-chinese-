package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.cost.PutUnderCost;
import open.batoru.data.ability.modifiers.PowerModifier;

import java.util.List;

public final class SIGNI_G1_CodeRideRiceHarvester extends Card {

    public SIGNI_G1_CodeRideRiceHarvester()
    {
        setImageSets("WX25-P1-090");

        setOriginalName("コードライド　イネカリキ");
        setAltNames("コードライドイネカリキ Koodo Raido Inekariki");
        setDescription("jp",
                "@C：このシグニのパワーはこのシグニの下にあるシグニ１枚につき＋2000される。\n" +
                "@E @[手札から共通するクラスを持たない緑のシグニ２枚をこのシグニの下に置く]@：【エナチャージ２】" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Code Ride Rice Harvester");
        setDescription("en",
                "@C: This SIGNI gets +2000 power for each SIGNI under this SIGNI.\n" +
                "@E @[Put 2 green SIGNI that do not share a common class from your hand under this SIGNI]@: [[Ener Charge 2]]." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

		setName("zh_simplified", "骑乘代号 水稻收割机");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据这只精灵的下面的精灵的数量，每有1张就+2000。\n" +
                "@E 从手牌把不持有共通类别的绿色的精灵2张放置到这只精灵的下面:[[能量填充2]]" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
            
            registerEnterAbility(new PutUnderCost(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().SIGNI().withColor(CardColor.GREEN).fromHand(), this::onEnterEffCostCond), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 2000 * new TargetFilter().own().SIGNI().under(getCardIndex()).except(CardType.SPELL).getValidTargetsCount();
        }
        
        private boolean onEnterEffCostCond(List<CardIndex> pickedCards)
        {
            return pickedCards.isEmpty() || (pickedCards.size() == 2 && !pickedCards.getFirst().getIndexedInstance().getSIGNIClass().matches(pickedCards.getLast().getIndexedInstance().getSIGNIClass()));
        }
        private void onEnterEff()
        {
            enerCharge(2);
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
