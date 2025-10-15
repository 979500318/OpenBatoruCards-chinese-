package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_IrisCrimsonAngel extends Card {
    
    public SIGNI_R2_IrisCrimsonAngel()
    {
        setImageSets("WXDi-P03-058");
        
        setOriginalName("紅天　イーリス");
        setAltNames("コウテンイーリス Kouten Iirisu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に＜天使＞のシグニが３種類以上ある場合、対戦相手のパワー8000以下のシグニ１体を対象とし、手札から＜天使＞のシグニを１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Iris, Crimson Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are three or more different <<Angel>> SIGNI on your field, you may discard an <<Angel>> SIGNI. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Iris, Crimson Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there are 3 or more types of <<Angel>> SIGNI on your field, target 1 of your opponent's SIGNI with power 8000 or less, and you may discard 1 <<Angel>> SIGNI from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "红天 伊里丝");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上的<<天使>>精灵在3种类以上的场合，对战对手的力量8000以下的精灵1只作为对象，可以从手牌把<<天使>>精灵1张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).getExportedData().stream().map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                
                if(target != null && discard(0,1, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL)).get() != null)
                {
                    banish(target);
                }
            }
        }
    }
}
