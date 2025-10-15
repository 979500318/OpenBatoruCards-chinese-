package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R1_JaponicaLuteaPhantomInsect extends Card {

    public SIGNI_R1_JaponicaLuteaPhantomInsect()
    {
        setImageSets("WX25-P2-074");

        setOriginalName("幻蟲　アカシジミ");
        setAltNames("ゲンチュウアカシジミ Genchuu Akashijimi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の＜凶蟲＞のシグニがある場合、対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Japonica Lutea, Phantom Insect");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another <<Misfortune Insect>> SIGNI on your field, target 1 of your opponent's SIGNI with power 2000 or less, and banish it."
        );

		setName("zh_simplified", "幻虫 黄灰蝶");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有其他的<<凶蟲>>精灵的场合，对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                banish(target);
            }
        }
    }
}
