package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_SamidareSmallEquipment extends Card {

    public SIGNI_R2_SamidareSmallEquipment()
    {
        setImageSets("WXDi-P09-057");

        setOriginalName("小装　サミダレ");
        setAltNames("ショウソウサミダレ Shousou Samidare");
        setDescription("jp",
                "@E %R @[手札を１枚捨てる]@：あなたのトラッシュから＜ウェポン＞のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Samidare, Lightly Armed");
        setDescription("en",
                "@E %R @[Discard a card]@: Add target <<Weapon>> SIGNI from your trash to your hand." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Samidare, Small Equipment");
        setDescription("en_fan",
                "@E %R @[Discard 1 card from your hand]@: Target 1 <<Weapon>> SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );

		setName("zh_simplified", "小装 五月雨");
        setDescription("zh_simplified", 
                "@E %R手牌1张舍弃:从你的废弃区把<<ウェポン>>精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            registerEnterAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.RED, 1)), new DiscardCost(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.WEAPON).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
