package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_MyuMemoriaPhantomInsect extends Card {
    
    public SIGNI_K2_MyuMemoriaPhantomInsect()
    {
        setImageSets("WXDi-P06-084", "WXDi-P06-084P", "SPDi01-81");
        
        setOriginalName("幻蟲　ミュウ//メモリア");
        setAltNames("ゲンチュウミュウメモリア Genchuu Myuu Memoria");
        setDescription("jp",
                "@A %K #D：あなたのトラッシュからレベル２以下の黒のシグニ１枚を対象とし、それを手札に加える。場にシグニに付いているカードかシグニの下に置かれているカードがある場合、代わりにあなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Myu//Memoria, Phantom Insect");
        setDescription("en",
                "@A %K #D: Add target black level two or less SIGNI from your trash to your hand. If there is a card underneath or attached to a SIGNI on the field, instead add target black SIGNI from your trash to your hand.  " +
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Myu//Memoria, Phantom Insect");
        setDescription("en_fan",
                "@A %K #D: Target 1 level 2 or lower black SIGNI from your trash, and add it to your hand. If there is a card attached to a SIGNI or a card placed under a SIGNI on the field, instead target 1 black SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "幻虫 缪//回忆");
        setDescription("zh_simplified", 
                "@A %K#D:从你的废弃区把等级2以下的黑色的精灵1张作为对象，将其加入手牌。场上的精灵有附加的牌或精灵的下面有放置牌的场合，作为替代，从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。（也参照对战对手的场上的牌）" +
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
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
            
            registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLACK, 1)),
                new DownCost()
            ), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash();
            if(new TargetFilter().SIGNI().withUnderType(CardUnderCategory.ATTACHED.getFlags() | CardUnderCategory.UNDER.getFlags()).getValidTargetsCount() == 0) filter = filter.withLevel(0,2);
            
            CardIndex target = playerTargetCard(filter).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
