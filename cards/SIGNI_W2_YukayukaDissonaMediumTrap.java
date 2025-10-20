package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_YukayukaDissonaMediumTrap extends Card {

    public SIGNI_W2_YukayukaDissonaMediumTrap()
    {
        setImageSets("WXDi-P12-059", "SPDi27-01");

        setOriginalName("中罠　ゆかゆか//ディソナ");
        setAltNames("チュウビンユカユカディソナ Chuubin Yukayuka Disona");
        setDescription("jp",
                "@A %W %W #D：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Yukayuka//Dissona, Medium Trickster");
        setDescription("en",
                "@A %W %W #D: Add target SIGNI with a #G from your trash to your hand." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Yukayuka//Dissona, Medium Trap");
        setDescription("en_fan",
                "@A %W %W #D: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't Attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "中罠 由香香//失调");
        setDescription("zh_simplified", 
                "@A %W %W:横置从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.WHITE, 2)), new DownCost()), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());

            draw(1);
        }
    }
}
