package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_MidorikoRepair extends Card {
    
    public LRIGA_G2_MidorikoRepair()
    {
        setImageSets("WXDi-P06-030");
        
        setOriginalName("緑姫・修復");
        setAltNames("ミドリコシュウフク Midoriko Shuufuku");
        setDescription("jp",
                "@E：あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。\n" +
                "@E %G %X %X %X：あなたのトラッシュから##を持たないカード１枚を対象とし、それをライフクロスに加える。"
        );
        
        setName("en", "Midoriko, Repair");
        setDescription("en",
                "@E: Shuffle your deck and add the top card of your deck to your Life Cloth.\n" +
                "@E %G %X %X %X: Add target card without ## from your trash to your Life Cloth."
        );
        
        setName("en_fan", "Midoriko Repair");
        setDescription("en_fan",
                "@E: Shuffle your deck and add the top card of it to life cloth.\n" +
                "@E %G %X %X %X: Target 1 card from your trash without ## @[Life Burst]@, and add it to life cloth."
        );
        
		setName("zh_simplified", "绿姬·修复");
        setDescription("zh_simplified", 
                "@E :你的牌组洗切把最上面的牌加入生命护甲。\n" +
                "@E %G%X %X %X从你的废弃区把不持有##的牌1张作为对象，将其加入生命护甲。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            shuffleDeck();
            addToLifeCloth(1);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().SIGNI().not(new TargetFilter().lifeBurst()).fromTrash()).get();
            addToLifeCloth(target);
        }
    }
}
