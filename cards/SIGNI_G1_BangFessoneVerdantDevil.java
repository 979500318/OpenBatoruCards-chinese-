package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G1_BangFessoneVerdantDevil extends Card {

    public SIGNI_G1_BangFessoneVerdantDevil()
    {
        setImageSets("WXDi-P16-077");

        setOriginalName("翠魔　バン//フェゾーネ");
        setAltNames("スイマバンフェゾーネ Suima Ban Fezoone");
        setDescription("jp",
                "@A %G %G @[このシグニを場からトラッシュに置く]@：対戦相手は自分のパワー10000以上のシグニ１体を選びエナゾーンに置く。"
        );

        setName("en", "Bang//Fesonne, Jade Evil");
        setDescription("en",
                "@A %G %G @[Put this SIGNI on your field into its owner's trash]@: Your opponent chooses a SIGNI on their field with power 10000 or more and puts it into its owner's Ener Zone."
        );
        
        setName("en_fan", "Bang//Fessone, Verdant Devil");
        setDescription("en_fan",
                "@A %G %G @[Put this SIGNI from the field into the trash]@: Your opponent chooses 1 of their SIGNI with power 10000 or more, and puts it into the ener zone."
        );

		setName("zh_simplified", "翠魔 梆//音乐节");
        setDescription("zh_simplified", 
                "@A %G %G这只精灵从场上放置到废弃区:对战对手选自己的力量10000以上的精灵1只放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.GREEN, 2)), new TrashCost()), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.ENER).own().SIGNI().withPower(10000,0)).get();
            putInEner(cardIndex);
        }
    }
}
