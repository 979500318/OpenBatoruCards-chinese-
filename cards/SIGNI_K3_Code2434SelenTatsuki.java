package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_Code2434SelenTatsuki extends Card {

    public SIGNI_K3_Code2434SelenTatsuki()
    {
        setImageSets("WXDi-CP01-051");

        setOriginalName("コード２４３４　セレン 龍月");
        setAltNames("コードニジサンジセレンタツキ Koodo Nijisanji Seren Tatsuki");
        setDescription("jp",
                "@E %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。あなたのトラッシュに＜バーチャル＞のシグニが１０枚以上ある場合、代わりにターン終了時まで、それのパワーを－10000する。"
        );

        setName("en", "Selen Tatsuki, Code 2434");
        setDescription("en",
                "@E %K: Target SIGNI on your opponent's field gets --8000 power until end of turn. If there are ten or more <<Virtual>> SIGNI in your trash, it gets --10000 power until end of turn instead.\n"
        );
        
        setName("en_fan", "Code 2434 Selen Tatsuki");
        setDescription("en_fan",
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power. If there are 10 or more <<Virtual>> SIGNI in your trash, instead, until end of turn, it gets --10000 power."
        );

		setName("zh_simplified", "2434代号 龙月Selen");
        setDescription("zh_simplified", 
                "@E %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。你的废弃区的<<バーチャル>>精灵在10张以上的场合，作为替代，直到回合结束时为止，其的力量-10000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().getValidTargetsCount() < 10 ? -8000 : -10000, ChronoDuration.turnEnd());
        }
    }
}
