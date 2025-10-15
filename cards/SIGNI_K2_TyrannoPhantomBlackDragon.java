package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_TyrannoPhantomBlackDragon extends Card {
    
    public SIGNI_K2_TyrannoPhantomBlackDragon()
    {
        setImageSets("WXDi-P06-083");
        
        setOriginalName("幻黒竜　ティラノ");
        setAltNames("ゲンコクリュウティラノ Genkokuryuu Tirano");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、このシグニの下からカード３枚をトラッシュに置き%X %Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－8000する。\n" +
                "@E：あなたのトラッシュからレベル１、レベル２、レベル３のシグニをそれぞれ１枚まで対象とし、それらをこのシグニの下に置く。"
        );
        
        setName("en", "Tyranno, Phantom Black Dragon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put three cards underneath this SIGNI into their owner's trash and pay %K %X. If you do, target SIGNI on your opponent's field gets --8000 power until end of turn.\n" +
                "@E: Put up to one target level one SIGNI, one target level two SIGNI, and one target level three SIGNI from your trash under this SIGNI."
        );
        
        setName("en_fan", "Tyranno, Phantom Black Dragon");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may put 3 cards from under this SIGNI into the trash and pay %K %X. If you do, until end of turn, it gets --8000 power.\n" +
                "@E: Target up to 1 level 1, 1 level 2 and 1 level 3 SIGNI each from your trash, and put them under this SIGNI."
        );
        
		setName("zh_simplified", "幻黑龙 暴龙");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以从这只精灵的下面把3张牌放置到废弃区并支付%K%X。这样做的场合，直到回合结束时为止，其的力量-8000。\n" +
                "@E :从你的废弃区把等级1、等级2、等级3的精灵各1张最多作为对象，将这些放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null &&
               payAll(new TrashCost(3, new TargetFilter().own().under(getCardIndex())), new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1))))
            {
                gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = new DataTable<>();
            
            for(int i=1;i<=3;i++)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.UNDER).own().SIGNI().withLevel(i).fromTrash().except(data)).get();
                if(target != null) data.add(target);
            }
            
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }
    }
}
