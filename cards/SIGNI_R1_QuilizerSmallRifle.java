package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_QuilizerSmallRifle extends Card {

    public SIGNI_R1_QuilizerSmallRifle()
    {
        setImageSets("WXDi-P11-057");

        setOriginalName("小銃　キライザ");
        setAltNames("ショウジュウキライザ Shoujuu Kiraiza");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に＜アーム＞のシグニがある場合、対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Kiraiza, Small Arms");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is an <<Armed>> SIGNI on your field, vanish target SIGNI on your opponent's field with power 2000 or less."
        );
        
        setName("en_fan", "Quilizer, Small Rifle");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is an <<Arm>> SIGNI on your field, target 1 of your opponent's SIGNI with power 2000 or less, and banish it."
        );

		setName("zh_simplified", "小铳 麻醉铳");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有<<アーム>>精灵的场合，对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                banish(target);
            }
        }
    }
}
