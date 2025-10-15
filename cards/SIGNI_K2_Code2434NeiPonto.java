package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_Code2434NeiPonto extends Card {

    public SIGNI_K2_Code2434NeiPonto()
    {
        setImageSets("WXDi-CP01-047");

        setOriginalName("コード２４３４　先斗寧");
        setAltNames("コードニジサンジポントネイ Koodo Nijisanji Ponto Nei");
        setDescription("jp",
                "@E %X：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。あなたのトラッシュに＜バーチャル＞のシグニが１０枚以上ある場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Ponto Nei, Code 2434");
        setDescription("en",
                "@E %X: Target SIGNI on your opponent's field gets --3000 power until end of turn. If there are ten or more <<Virtual>> SIGNI in your trash, it gets --5000 power until end of turn instead."
        );
        
        setName("en_fan", "Code 2434 Nei Ponto");
        setDescription("en_fan",
                "@E %X: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. If there are 10 or more <<Virtual>> SIGNI in your trash, until end of turn, it gets --5000 power instead."
        );

		setName("zh_simplified", "2434代号 先斗宁");
        setDescription("zh_simplified", 
                "@E %X:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。你的废弃区的<<バーチャル>>精灵在10张以上的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().getValidTargetsCount() < 10 ? -3000 : -5000, ChronoDuration.turnEnd());
        }
    }
}
