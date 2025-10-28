package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class ARTS_W_InSearchOfHiddenHerritage extends Card {

    public ARTS_W_InSearchOfHiddenHerritage()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-026");

        setOriginalName("隠されし遺産を求めて");
        setAltNames("カクセレシイサンヲモトメテ Kakusa Reshi Isan wo Motomote");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のルリグかシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "$$2対戦相手のルリグとシグニを合計２体まで対象とし、あなたのエナゾーンから＜ブルアカ＞のカード２枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、それらは@>@C：アタックできない。@@を得る。"
        );

        setName("en", "In Search of Hidden Herritage");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's LRIG or SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "$$2 Target up to 2 LRIG and/or SIGNI, and you may put 2 <<Blue Archive>> cards from your ener zone into the trash. If you do, until end of turn, they gain:" +
                "@>@C: Can't attack."
        );

        setName("es", "En busca de la herencia oculta.");
        setDescription("es",
                "@[@|Elige 1 de los siguientes:|@]@\n" +
                "$$1 Selecciona 1 LRIG o SIGNI oponente y hasta el final del turno, esta gana:" +
                "@>@C: No puede atacar.@@" +
                "$$2 Selecciona hasta 2 LRIG y/o SIGNI y puedes poner 2 cartas <<Blue Archive>> de tu zona ener a la basura. Si lo haces, hasta el final del turno, las LRIG/SIGNI seleccionadas ganan:" +
                "@>@C: No puede atacar."
        );

        setName("zh_simplified", "寻找隐藏的遗产");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的分身或精灵1只作为对象，直到回合结束时为止，其得到" +
                "@>@C :不能攻击。@@" +
                "$$2 对战对手的分身和精灵合计2只最多作为对象，可以从你的能量区把<<蔚蓝档案>>牌2张放置到废弃区。这样做的场合，直到回合结束时为止，这些得到" +
                "@>@C :不能攻击。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setModeChoice(1);
        }
        
        private void onARTSEff()
        {
            if(arts.getChosenModes() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();
                attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            } else {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).OP().fromField());
                if(data.get() != null)
                {
                    DataTable<CardIndex> dataToTrash = playerTargetCard(0,2, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
                    if(trash(dataToTrash) == 2) for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityCantAttack(), ChronoDuration.turnEnd());
                }
            }
        }
    }
}
