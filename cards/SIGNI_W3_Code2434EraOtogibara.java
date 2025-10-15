package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_Code2434EraOtogibara extends Card {
    
    public SIGNI_W3_Code2434EraOtogibara()
    {
        setImageSets("WXDi-D02-23");
        setLinkedImageSets("WXDi-D02-22");
        
        setOriginalName("コード２４３４　御伽原江良");
        setAltNames("コードニジサンジオトギバラエラ Koodo Nijisanji Otogibara Era");
        setDescription("jp",
                "@E %X %X：あなたの場に《コード２４３４　森中花咲》がある場合、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E %W %W %X：あなたの場に＜バーチャル＞のシグニが３体以上ある場合、対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Era Otogibara, Code 2434");
        setDescription("en",
                "@E %X %X: If there is a \"Kazaki Morinaka, Code 2434\" on your field, add target SIGNI with #G from your trash to your hand.\n" +
                "@E %W %W %X: If there are three or more <<Virtual>> SIGNI on your field, return target SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Code 2434 Era Otogibara");
        setDescription("en_fan",
                "@E %X %X: If there is a \"Code 2434 Morinaka Kazaki\" on your field, target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand.\n" +
                "@E %W %W %X: If there are 3 or more <<Virtual>> SIGNI on your field, target 1 of your opponent's SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "2434代号 御伽原江良");
        setDescription("zh_simplified", 
                "@E %X %X你的场上有《コード２４３４　森中花咲》的场合，从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "@E %W %W%X:你的场上的<<バーチャル>>精灵在3只以上的场合，对战对手的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 2) + Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            if(new TargetFilter().own().SIGNI().withName("コード２４３４　森中花咲").getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
                addToHand(target);
            }
        }
        
        private void onEnterEff2()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount() >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                addToHand(target);
            }
        }
    }
}
