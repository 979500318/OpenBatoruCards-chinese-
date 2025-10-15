package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExcludeCost;

public final class SIGNI_K3_DreiMisfotunePrincessPandora extends Card {

    public SIGNI_K3_DreiMisfotunePrincessPandora()
    {
        setImageSets("WXDi-P11-049");

        setOriginalName("ドライ＝厄姫パンドラ");
        setAltNames("ドライヤッキパンドラ Dorai Yakki Pandora");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、あなたのトラッシュにある＜毒牙＞のシグニ３枚をゲームから除外し%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n" +
                "@E @[手札から＜毒牙＞のシグニを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Troublesome Queen Pandora: Drei");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may remove three <<Venom>> SIGNI in your trash from the game and pay %K. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n" +
                "@E @[Discard a <<Venom>> SIGNI]@: Target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Drei-Misfortune Princess Pandora");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may exclude 3 <<Venom Fang>> SIGNI in your trash from the game and pay %K. If you do, until end of turn, it gets --10000 power.\n" +
                "@E @[Discard 1 <<Venom Fang>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "DREI＝厄姬潘多拉");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以把你的废弃区的<<毒牙>>精灵3张从游戏除外并支付%K。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@E 从手牌把<<毒牙>>精灵1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(3);
        setPower(10000);

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
            
            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.VENOM_FANG)), this::onEnterEff);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payAll(new ExcludeCost(3, new TargetFilter().SIGNI().withClass(CardSIGNIClass.VENOM_FANG).fromTrash()), new EnerCost(Cost.color(CardColor.BLACK, 1))))
            {
                gainPower(target, -10000, ChronoDuration.turnEnd());
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -5000, ChronoDuration.turnEnd());
        }
    }
}
