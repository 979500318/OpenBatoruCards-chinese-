package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R3_PythonGunRoaringGun extends Card {

    public SIGNI_R3_PythonGunRoaringGun()
    {
        setImageSets("WX25-P1-078");

        setOriginalName("轟砲　パイソンガン");
        setAltNames("ゴウホウパイソンガン Gouhou Paisongan");
        setDescription("jp",
                "@E %R %X：対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。あなたの場にクロス状態のシグニがある場合、代わりに対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Python Gun, Roaring Gun");
        setDescription("en",
                "@E %R %X: Target 1 of your opponent's SIGNI with power 12000 or less, and banish it. If there are crossed SIGNI on your field, instead target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "轰炮 蟒蛇转轮手枪");
        setDescription("zh_simplified", 
                "@E %R%X:对战对手的力量12000以下的精灵1只作为对象，将其破坏。你的场上有交错状态的精灵的场合，作为替代，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
            if(new TargetFilter().own().SIGNI().crossed().getValidTargetsCount() == 0) filter = filter.withPower(0,12000);
            CardIndex target = playerTargetCard(filter).get();
            banish(target);
        }
    }
}
