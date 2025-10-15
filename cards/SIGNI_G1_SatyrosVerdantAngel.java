package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.AbilityCost.Payable;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityLancer;

import java.util.HashSet;
import java.util.Set;

public final class SIGNI_G1_SatyrosVerdantAngel extends Card {
    
    public SIGNI_G1_SatyrosVerdantAngel()
    {
        setImageSets("WXDi-P04-071");
        
        setOriginalName("翠天　サテュロス");
        setAltNames("スイテンサテュロス Suiten Saturosu Satyros");
        setDescription("jp",
                "@E @[エナゾーンから＜天使＞のシグニ３枚をトラッシュに置く]@：この方法でトラッシュに置いたカードが持つ色が合計３種類以上ある場合、あなたの＜天使＞のシグニ１体を対象とし、ターン終了時まで、それは[[ランサー]]を得る。"
        );
        
        setName("en", "Satyr, Jade Angel");
        setDescription("en",
                "@E @[Put three <<Angel>> SIGNI from your Ener Zone into your trash]@: If the cards put into the trash this way consisted of three or more colors, target <<Angel>> SIGNI on your field gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Sátyros, Verdant Angel");
        setDescription("en_fan",
                "@E @[Put 3 <<Angel>> SIGNI from your ener zone into the trash]@: If there are 3 or more colors among the SIGNI put into the trash this way, target 1 of your <<Angel>> SIGNI, and until end of turn, it gains [[Lancer]]."
        );
        
		setName("zh_simplified", "翠天 萨提洛斯");
        setDescription("zh_simplified", 
                "@E 从能量区把<<天使>>精灵3张放置到废弃区:这个方法放置到废弃区的牌持有颜色合计3种类以上的场合，你的<<天使>>精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new TrashCost(3, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner()), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            DataTable<Payable> dataPaidCost = getAbility().getCostPaidData();
            Set<CardColor> cacheColors = new HashSet<>();
            for(int i=0;i<dataPaidCost.size();i++)
            {
                cacheColors.addAll(((CardIndexSnapshot)dataPaidCost.get(i)).getColor().getValue());
                
                if(cacheColors.size() >= 3)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.ANGEL)).get();
                    if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
                    
                    break;
                }
            }
        }
    }
}
