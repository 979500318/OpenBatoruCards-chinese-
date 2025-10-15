package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_G2_SangaReboot extends Card {
    
    public LRIGA_G2_SangaReboot()
    {
        setImageSets("WXDi-P03-032");
        
        setOriginalName("サンガ／／リブート");
        setAltNames("サンガリブート Sanga Ribuuto");
        setDescription("jp",
                "@E：対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：[[エナチャージ３]]をする。その後、あなたのエナゾーンからカードを２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Sanga//Reboot");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 10000 or more.\n" +
                "@E: [[Ener Charge 3]]. Then, add up to two target cards from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Sanga//Reboot");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it.\n" +
                "@E: [[Ener Charge 3]]. Then, target up to 2 cards from your ener zone, and add them to your hand."
        );
        
		setName("zh_simplified", "山河//重启");
        setDescription("zh_simplified", 
                "@E :对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n" +
                "@E :[[能量填充3]]。然后，从你的能量区把牌2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            enerCharge(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromEner());
            addToHand(data);
        }
    }
}
